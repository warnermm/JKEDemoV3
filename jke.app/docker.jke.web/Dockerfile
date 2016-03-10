FROM ci/wlp:latest
RUN mkdir -p /tmp/staging
ADD jke /tmp/staging
RUN cp /tmp/staging/jke.war /opt/wlp/usr/servers/ci/dropins/.