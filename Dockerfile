FROM dhyaneshm/centos-java-8
ADD build/distributions/vlotot-1.0.0-1.noarch.rpm /opt/vlotot-1.0.0-1.noarch.rpm
RUN yum localinstall /opt/vlotot-1.0.0-1.noarch.rpm -y && rm -f /opt/vlotot-1.0.0-1.noarch.rpm
RUN bash -c 'mkdir -p /data1/log/vlotot'

RUN groupadd ta && useradd -ms /bin/bash ta
USER ta
