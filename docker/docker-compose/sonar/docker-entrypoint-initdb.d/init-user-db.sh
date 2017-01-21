#!/bin/sh
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE USER sonar LOGIN PASSWORD 'sonar';
    CREATE DATABASE sonar OWNER sonar;
EOSQL
