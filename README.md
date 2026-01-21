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

### Configure the DNS zone

#### List of DNS records

## Deployment instructions





## How to use the application wiht Curl

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


