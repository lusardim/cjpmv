#!/bin/bash
user=root
pass=root
drop='drop schema cjpmv;'
create='create database cjpmv'
mysql -u $user -p$pass -e "$drop";
mysql -u $user -p$pass -e "$create";
mysql -u $user -p$pass cjpmv </home/pulpol/workspaceNuevoSVN/prestamos/sql/base.sql


