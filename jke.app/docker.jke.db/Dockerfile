FROM dtr.ibm.hybrid.cloud.com/ci_jke/mysql:latest
RUN mkdir -p /tmp/staging
ADD jke /tmp/staging
COPY entry.sh /root/
RUN chmod 744 /root/entry.sh
ENTRYPOINT exec /root/entry.sh