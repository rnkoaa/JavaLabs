FROM jenkinsci/jenkins:2.7

MAINTAINER Richard Agyei <richard.agyei@tr.com>

# Hard coding these environment variables, probably a good idea to pass them through.
ENV ANT_VERSION 1.9.7
ENV GRADLE_VERSION 2.13

USER root
#RUN apt-get update && apt-get install -y ant

RUN apt-get update && apt-get install -y \
    unzip \
    wget

RUN cd /opt && wget -q https://www.apache.org/dist/ant/binaries/apache-ant-$ANT_VERSION-bin.zip > /dev/null \
    && unzip -q apache-ant-$ANT_VERSION-bin.zip > /dev/null \
    && rm apache-ant-$ANT_VERSION-bin.zip \
    && rm -rf /opt/apache-ant-$ANT_VERSION/manual/ \
    && rm /opt/apache-ant-$ANT_VERSION/CONTRIBUTORS \
    && rm /opt/apache-ant-$ANT_VERSION/INSTALL \
    && rm /opt/apache-ant-$ANT_VERSION/NOTICE \
    && rm /opt/apache-ant-$ANT_VERSION/README \
    && rm /opt/apache-ant-$ANT_VERSION/WHATSNEW

ENV ANT_HOME /opt/apache-ant-$ANT_VERSION

RUN cd /opt && wget -q https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip > /dev/null \
    && unzip -q gradle-$GRADLE_VERSION-bin.zip > /dev/null \
    && rm gradle-$GRADLE_VERSION-bin.zip \
    && apt-get remove -y unzip

ENV GRADLE_HOME /opt/gradle-$GRADLE_VERSION

ENV PATH $PATH:$ANT_HOME/bin
ENV PATH $PATH:$GRADLE_HOME/bin

USER jenkins

# COPY plugins.txt /var/jenkins_home/plugins.txt
# RUN /usr/local/bin/plugins.sh /var/jenkins_home/plugins.txt
RUN echo 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state

COPY jobs/seed-1/config.xml /usr/share/jenkins/ref/jobs/seed/config.xml
COPY jobs/seed-2/adwubi.groovy /usr/share/jenkins/ref/jobs/seed/workspace/adwubi.groovy

RUN /usr/local/bin/install-plugins.sh git github dashboard-view github-organization-folder \
    jobConfigHistory email-ext job-dsl credentials-binding workflow-aggregator tfs gradle ant

# to indicate that this Jenkins installation is fully configured.
# Otherwise a banner will appear prompting the user to install additional plugins, which may be inappropriate.
#RUN echo 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state
