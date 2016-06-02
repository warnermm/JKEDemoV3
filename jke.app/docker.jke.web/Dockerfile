FROM dtr.ibm.hybrid.cloud.com/ci_jke/wlp:latest
RUN mkdir -p /tmp/staging
ADD jke /tmp/staging
RUN cp /tmp/staging/jke.war /opt/wlp/usr/servers/ci/dropins/.
COPY entry.sh /root/
RUN chmod 744 /root/entry.sh
ENTRYPOINT exec /root/entry.sh