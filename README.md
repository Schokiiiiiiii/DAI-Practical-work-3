# DAI Practical work 3

## Tableof contents

- Content1

## General

- Authors: [Fabien Léger](https://github.com/Schokiiiiiiii) & [Samuel Dos Santos](https://github.com/Samurai-05)
- Course: DAI, HEIG-VD
- Date: 17.12.2025

## Overview

The API definition can be found here
[Link to API](doc/API.md)

## Installations ans configurations

### Virtual machine

The installation  of the virtual machine was done by following the instruction given 
in the DAI course [here](https://github.com/heig-vd-dai-course/heig-vd-dai-course/blob/main/11.03-ssh-and-scp/01-course-material/README.md#practical-content)

### Configure the DNS zone

Our domain name was obtained through the services of [Dynu systems](https://www.dynu.com/) which is a free DNS provider.

There we chose our domain name and provided the A record (VM's IP).

#### List of DNS records

## Deployment instructions

Run docker

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
- `Id-Unmodified-Since`
  - Direction : Request
  - Used in PUT and DELETE requests to prevent lost updates
