# DAI Practical work 3

The API can be accessed here : [cosmic-latte.loseyourip.com](https://cosmic-latte.loseyourip.com)

## Table of contents

- [General](#general)
- [Overview](#overview)
- [Installations and configurations](#installations-and-configurations)
- [Deployment instructions](#steps-to-deploy-run-and-access)
- [How to use the application with Curl](#how-to-use-the-application-with-curl)
- [Caching strategy](#caching-strategy)

## General

- Authors: [Fabien Léger](https://github.com/Schokiiiiiiii) & [Samuel Dos Santos](https://github.com/Samurai-05)
- Course: DAI, HEIG-VD
- Date: 17.12.2025

## Overview

This repository contains the third practical work. The goal is to define and implement a CRUD API
application and deploy it to a remote server so that everyone can access it.

The API is named `Cosmic Latte` and allows users to catalog astronomical objects such as planets,
stars, and black holes.

The API definition can be found here :
[Link to API](doc/API.md)

## Installations and configurations

### Virtual machine

The installation  of the virtual machine was done by following the instruction given 
in the DAI course [here](https://github.com/heig-vd-dai-course/heig-vd-dai-course/blob/main/11.03-ssh-and-scp/01-course-material/README.md#practical-content)

### Configure the DNS zone

Our domain name was obtained through the services of [Dynu systems](https://www.dynu.com/) which is a free DNS provider.

There we chose our domain name and provided the A record (VM's IP).

#### List of DNS records

The DNS recorded name and A record :
- `cosmic-latte.loseyourip.com`
- A record : 4.235.110.154


![img.png](img.png)

Two domains (one domain and subdomain) have been defined for this project:

- [cosmic-latte.loseyourip.com](https://cosmic-latte.loseyourip.com) - API
- [traefik.cosmic-latte.loseyourip.com](https://traefik.cosmic-latte.loseyourip.com) - Traefik dashboard

## steps to deploy, run and access

### Replace the domain names

Open the files below and change the required values.

- `docker/cosmic/.env` - COSMIC_FULLY_QUALIFIED_DOMAIN_NAME=<domain_name> (i.e. cosmic-latte.loseyourip.com)
- `docker/traefik/.env` - TRAEFIK_FULLY_QUALIFIED_DOMAIN_NAME=traefik.<domain_name> (i.e. traefik.cosmic-latte.loseyourip.com)

This is required to tell traefik how to solve domain names.

### Copy files onto the vm

On your local machine you can do similar commands to copy from the repository root onto your VM. The application files and the traefik files need to be in separate directories especially since they have different .env files.

```bash
# copy compose and .env for app
scp docker/cosmic/compose.yaml <username>@<vm public ip>:~/cosmicLatteAPI/app/compose.yaml
scp docker/cosmic/.env <username>@<vm public ip>:~/cosmicLatteAPI/app/.env
```

```bash
# copy compose and .env for traefik
scp docker/traefik/compose.yaml <username>@<vm public ip>:~/cosmicLatteAPI/traefik/compose.yaml
scp docker/traefik/.env <username>@<vm public ip>:~/cosmicLatteAPI/traefik/.env
```

### Enable ports on the VM

Check that your VM firewall is accepting http and https connections.

```bash
sudo ufw status
```

If this tells you `active`, then do the following command.

```bash
sudo ufw allow 80
sudo ufw allow 443
sudo ufw reload
```

### Launch the application

Start both containers inside your VM.

`/cosmicLatteAPI/app/compose.yaml`

```bash
docker compose up -d
```
- `-d` - runs container in the background

`/cosmicLatteAPI/traefik/compose.yaml`

```bash
docker compose up -d
```
- `-d` - runs container in the background

### Access the traefik dashboard

You will see if your setup works. This might take some minutes for certificate to go up especially depending on DNS provide.

You can access it by going to this address on any web browser.

```
https://traefik.<domain_name>
```

Or if it doesn't redirect you to the dash board to the following URL.

```
https://traefik.<domain_name>/dashboard/
```

You can also test it with your domain name directly.

```
https://<domain_name>
```

Which should return something along the lines of this.

`Endpoint GET / not found`

## How to use the application with Curl

A file with curl commands has been created, it also has a test scenario.

The file can be found here : [curl commands](doc/CURL_TESTS.md)

## Caching strategy

The API uses HTTP conditional requests with timestamp based cache validation.
This is a server side caching approach that uses HTTP headers.

### Headers used

- `Last-Modified`
  - Direction : Response
  - Timestamp of when the resource was last modified
- `If-Modified-Since`
  - Direction : Request
  - Used in GET requests. Client sends its last known timestamp
- `If-Unmodified-Since`
  - Direction : Request
  - Used in PUT and DELETE requests to prevent lost updates
