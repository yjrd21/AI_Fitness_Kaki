#!/usr/bin/env bash

set -u

ROOT="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
services=(eureka userservice activityservice aiservice)
pids=()

cleanup() {
    trap - TERM INT EXIT
    for pid in "${pids[@]}"; do
        kill "$pid" 2>/dev/null || true
    done
}

trap cleanup TERM INT EXIT

for service in "${services[@]}"; do
    (
        cd "$ROOT/$service" || exit 1
        exec mvn spring-boot:run
    ) &
    pids+=("$!")
done

wait
