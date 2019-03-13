FROM nimmis/java-centos:oracle-8-jdk
ADD build/distributions/vlotot-1.0.0-1.noarch.rpm /opt/vlotot-1.0.0-1.noarch.rpm
RUN yum localinstall /opt/vlotot-1.0.0-1.noarch.rpm -y && rm -f /opt/vlotot-1.0.0-1.noarch.rpm
RUN bash -c 'mkdir -p /data1/log/appuser'

WORKDIR /opt/vlotot/
RUN chmod +x startUpScript.sh
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.5.0/wait /wait
RUN chmod +x /wait

RUN useradd -ms /bin/bash appuser
USER appuser


