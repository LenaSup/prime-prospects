module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.aop;
    requires spring.tx;
    requires spring.jdbc;
    requires spring.webmvc;
    requires spring.web;
    requires spring.expression;
    requires spring.data.jpa;

    requires jakarta.persistence;

    requires spring.security.config;
    requires spring.security.web;
    requires spring.security.crypto;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.security.core;

    requires org.json;
    requires java.net.http;


    requires com.fasterxml.jackson.databind;

    requires org.hibernate.orm.core;
    requires java.desktop;



    requires jjwt.jackson;
    requires jjwt.impl;
    requires jjwt.api;
    requires static lombok;
    requires org.apache.tomcat.embed.core;
    requires spring.data.commons;


    opens org.example.prime_prospects_api.essence to
            spring.core, spring.beans, spring.data.commons, com.fasterxml.jackson.databind,
            spring.context, org.hibernate.commons.annotations, org.hibernate.orm.core, spring.security.core;
    opens org.example.prime_prospects_api to
            spring.core, spring.beans, spring.context,
            com.fasterxml.jackson.databind, spring.aop, org.hibernate.commons.annotations, org.hibernate.orm.core, spring.security.core;
    opens org.example.prime_prospects_api.essence.parsers to
            spring.core, spring.beans, spring.data.commons, spring.context,
            com.fasterxml.jackson.databind, org.hibernate.commons.annotations, org.hibernate.orm.core, spring.security.core;


    opens org.example.prime_prospects to javafx.fxml;
    exports org.example.prime_prospects;
    exports org.example.prime_prospects.contrallers;
    opens org.example.prime_prospects.contrallers to javafx.fxml;

    exports org.example.prime_prospects_api;
    exports org.example.prime_prospects_api.essence;
    exports org.example.prime_prospects_api.essence.parsers;
    exports org.example.prime_prospects_api.UserElements;
    exports org.example.prime_prospects_api.ResumeElements;
    exports org.example.prime_prospects_api.VacansyElements;
    opens org.example.prime_prospects_api.UserElements to com.fasterxml.jackson.databind, org.hibernate.commons.annotations, org.hibernate.orm.core, spring.aop, spring.beans, spring.context, spring.core, spring.security.core;
    opens org.example.prime_prospects_api.ResumeElements to com.fasterxml.jackson.databind, org.hibernate.commons.annotations, org.hibernate.orm.core, spring.aop, spring.beans, spring.context, spring.core, spring.security.core;
    opens org.example.prime_prospects_api.VacansyElements to com.fasterxml.jackson.databind, org.hibernate.commons.annotations, org.hibernate.orm.core, spring.aop, spring.beans, spring.context, spring.core, spring.security.core;
    exports org.example.prime_prospects_api.ResponseElements;
    opens org.example.prime_prospects_api.ResponseElements to com.fasterxml.jackson.databind, org.hibernate.commons.annotations, org.hibernate.orm.core, spring.aop, spring.beans, spring.context, spring.core, spring.security.core;

}