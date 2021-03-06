Jerry
==================
Collect tomcat logs and show them beautifully

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


Documentation
-------------
![main view](./images/mainView.PNG)
It's to quickly analyze and view tomcat logs in real time without needing to use your terminal.


Quick Start
-----------

1. Modify application.properties.xml
```
- server.port = insert your port
- source.dir.path = source folder path to explore
- source.package.prefix = com.example
- log.file.path = tomcat log file path to explore
- accesslog.file.path = access log file path to explore
- admin.user.id = your admin id
- admin.user.password = your admin password
```
2. Run command
```
$ cd {project}/src/main/resources/static/
$ npm install
$ npm run build
$ cd {project}/
$ mvn spring-boot:run
```
3. Connect "localhost:port"

4. Check Web Sockets Connection
    - tomcat log
    - apache log
    - error log
    - usage log

Requirements
-----------

If you want to bootstrap Maven, you'll need:
- Java 1.8+
- Maven 3.0.5+
- node 10.15.1+
- npm 6.5.1+

MIT License
-----------
    Copyright (c) 2019 Sangwoo Son

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
