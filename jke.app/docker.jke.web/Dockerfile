FROM sevtekin/jke_wlp
RUN mkdir -p /tmp/staging
ADD jke /tmp/staging
RUN cp /tmp/staging/jke.war /opt/wlp/usr/servers/ci/dropins/.
COPY entry.sh /root/
RUN chmod 744 /root/entry.sh
ENTRYPOINT exec /root/entry.sh