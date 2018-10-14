# tkachuk-it-academy

Project allows to make make actions (read, udpate, save , delete) with database table [Clients]

Environment preporation:
1.Be sure that you have working postgress database instance
2.To set database properties such as user , password, url, etc. please open find database.properties in resources of module database
and edit for your system configuration

For building war for Tomcat
1.Download project and open project
2.Open cmd in working directory where parent pom.xml is situated
3.Run maven command: mvn clean package
4.Then find ROOT.war in just created /target diractory of the project
5.Put it to Tomcat (C:\Program Files\apache-tomcat-9.0.12\webapps\), make sure it got unpacked
6.Open your tomcat in browser and enjoy)
