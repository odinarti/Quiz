<#macro page>
<#import "header.ftlh" as h>
<#include "security.ftlh">
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Quizzee</title>
		<link rel="stylesheet" type="text/css" href="../style.css">
		<link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=KoHo&display=swap" rel="stylesheet">
	</head>
	<body>
		<@h.header></@h.header>
		<#nested>
	</body>
</html>
</#macro>

<#macro formField type id label array=id testCategory="" value="">
	<div class="formField">
		<label for="${id}">${label}</label>
		<#if type == "select">
			<select id="${id}" name="${id}">
				<#list array?split(', ') as category>
					<#if category == testCategory>
						<option selected>${category}</option>
					<#else>
						<option>${category}</option>
					</#if>
					
				</#list>
			</select>
		<#else>
			<input type="${type}" class="textInput" name="${id}" id="${id}" value="${value}"/>
		</#if>
	</div>
</#macro>

<#macro labelField name value label>
	<div class="formField">
		<#if name == "roles">
			<label class="leftLabel" for="${name}">${label}</label>
			<label class="rightLabel" for="${name}" id="${name}">
				<#list nameroles as role>
					${role}
				</#list>
			</label>
		<#else>
			<label class="leftLabel" for="${name}">${label}</label>
			<label class="rightLabel" for="${name}" id="${name}">${value}</label>
		</#if>
	</div>
</#macro>