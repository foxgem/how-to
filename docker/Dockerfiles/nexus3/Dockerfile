FROM       openjdk:8-jre-alpine
MAINTAINER FengYu <abcfy2@163.com>

ENV NEXUS_VERSION 3.1.0-04

ENV SONATYPE_DIR /opt/sonatype
ENV NEXUS_HOME ${SONATYPE_DIR}/nexus
ENV NEXUS_DATA /nexus-data
ENV NEXUS_CONTEXT ''
ENV SONATYPE_WORK ${SONATYPE_DIR}/sonatype-work

# install nexus
RUN apk update && apk add openssl && rm -fr /var/cache/apk/*
RUN mkdir -p /opt/sonatype/ \
  && wget https://download.sonatype.com/nexus/3/nexus-${NEXUS_VERSION}-unix.tar.gz -O - \
  | tar zx -C "${SONATYPE_DIR}" && rm -fr ${SONATYPE_WORK} \
  && mv "${SONATYPE_DIR}/nexus-${NEXUS_VERSION}" "${NEXUS_HOME}"

# configure nexus
RUN sed \
    -e '/^nexus-context/ s:$:${NEXUS_CONTEXT}:' \
    -i ${NEXUS_HOME}/etc/nexus-default.properties

## create nexus user
RUN adduser -S -u 200 -D -H -h "${NEXUS_DATA}" -s /bin/false nexus nexus

RUN mkdir -p "${NEXUS_DATA}/etc" "${NEXUS_DATA}/log" "${NEXUS_DATA}/tmp" "${SONATYPE_WORK}"
RUN ln -s ${NEXUS_DATA} ${SONATYPE_WORK}/nexus3

## prevent warning: /opt/sonatype/nexus/etc/org.apache.karaf.command.acl.config.cfg (Permission denied)
RUN chown -R nexus "${NEXUS_HOME}/etc/"

COPY entrypoint.sh /

VOLUME ${NEXUS_DATA}

EXPOSE 8081
WORKDIR ${NEXUS_HOME}

ENV JAVA_MAX_MEM 1200m
ENV JAVA_MIN_MEM 1200m
ENV EXTRA_JAVA_OPTS ""

ENTRYPOINT ["/entrypoint.sh"]
