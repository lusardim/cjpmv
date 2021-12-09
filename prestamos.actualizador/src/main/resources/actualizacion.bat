@echo off

set CLASES=Prestamos_lib/antlr-2.7.6.jar;Prestamos_lib/hibernate-tools-3.2.0.beta9a.jar;Prestamos_lib/jtidy-r8-20060801.jar
set CLASES=%CLASES%;Prestamos_lib/jfreechart-1.0.12.jar;Prestamos_lib/jcommon-1.0.15.jar;Prestamos_lib/swing-worker-1.1.jar
set CLASES=%CLASES%;Prestamos_lib/filters-2.0.235.jar;Prestamos_lib/commons-digester-1.7.jar;Prestamos_lib/asm-1.5.3.jar
set CLASES=%CLASES%;Prestamos_lib/jcalendar-1.3.2.jar;Prestamos_lib/hibernate-3.2.2.ga.jar;Prestamos_lib/persistence-api-1.0.jar
set CLASES=%CLASES%;Prestamos_lib/itext-2.1.0.jar;Prestamos_lib/mysql-connector-java-5.1.10.jar;Prestamos_lib/commons-beanutils-1.8.0.jar
set CLASES=%CLASES%;Prestamos_lib/hsqldb-1.8.0.7.jar;Prestamos_lib/hibernate-annotations-3.2.1.ga.jar;Prestamos_lib/bcprov-jdk14-136.jar
set CLASES=%CLASES%;Prestamos_lib/commons-lang-2.5.jar;Prestamos_lib/commons-collections-2.1.jar;Prestamos_lib/asm-attrs-1.5.3.jar
set CLASES=%CLASES%;Prestamos_lib/cglib-2.1_3.jar;Prestamos_lib/javassist-3.3.ga.jar;Prestamos_lib/ehcache-1.2.3.jar
set CLASES=%CLASES%;Prestamos_lib/xml-apis-1.3.02.jar;Prestamos_lib/hibernate-entitymanager-3.2.1.ga.jar
set CLASES=%CLASES%;Prestamos_lib/appframework-1.03.jar;Prestamos_lib/commons-logging-1.0.2.jar;Prestamos_lib/c3p0-0.9.1.jar
set CLASES=%CLASES%;Prestamos_lib/swingx-1.6.1.jar;Prestamos_lib/freemarker-2.3.4.jar;Prestamos_lib/jta-1.1.jar;Prestamos_lib/jdtcore-3.1.0.jar
set CLASES=%CLASES%;Prestamos_lib/jboss-archive-browsing-5.0.0alpha-200607201-119.jar;Prestamos_lib/dom4j-1.6.1.jar;Prestamos_lib/jasperreports-3.5.3.jar
set CLASES=%CLASES%;Prestamos_lib/swing-layout-1.0.3.jar;Prestamos_lib/bcmail-jdk14-136.jar
set CLASES=%CLASES%;Prestamos.jar;Actualizador.jar

java -cp %CLASES% ar.gov.cjpmv.prestamos.actualizador.Parche3