<#--
/*
 * $Id: head.ftl 590812 2007-10-31 20:32:54Z apetrelli $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
-->
<link rel="stylesheet" href="<@s.url value='/struts/xhtml/styles.css' includeParams='none' encode='false' />" type="text/css"/>
<#include "/${parameters.templateDir}/simple/head.ftl" />

<#--
Custom by Robert Julius start here
-->
<script src="${base}/js/ganesha-ui-0.1.js" type="text/javascript"></script>
<link rel="stylesheet" href="<@s.url value='/css/ganesha-common-0.1.css' includeParams='none' encode='false' />" type="text/css"/>
<link rel="stylesheet" href="<@s.url value='/css/ganesha-struts2-0.1.css' includeParams='none' encode='false' />" type="text/css"/>
<link rel="stylesheet" href="<@s.url value='/css/ganesha-table-0.1.css' includeParams='none' encode='false' />" type="text/css"/>