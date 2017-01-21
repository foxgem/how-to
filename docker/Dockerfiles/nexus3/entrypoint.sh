#!/bin/sh -e

[ -d "${NEXUS_DATA}" ] || mkdir -p "${NEXUS_DATA}"
[ $(stat -c '%U' "${NEXUS_DATA}") != 'neuxs' ] && chown -R nexus "${NEXUS_DATA}"

# clear tmp and cache for upgrade
rm -fr "${NEXUS_DATA}"/tmp/ "${NEXUS_DATA}"/cache/

[ $# -eq 0 ] && \
    exec su -s /bin/sh -c '/opt/sonatype/nexus/bin/nexus run' nexus || \
    exec "$@"
