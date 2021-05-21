<#macro header>
<#include "security.ftlh">
	<header>
		<div id="logoDiv"><a href="/" class="aLogo">Quizzee</a>
			<#if roles?? && roles?seq_contains("ROLE_TESTER")>
				<a href="/create"><button class="greenButton">Создать тест</button></a>
			</#if>
			<#if roles?? && roles?seq_contains("ROLE_ADMIN")>
				<a href="/admin"><button class="greenButton">Панель администратора</button></a>
			</#if>
		</div>
		<div id="headerButtonsDiv">
			<#if user??>
				<a href="/search"><button class="greenButton">Поиск тестов</button></a>
				<a href="/profile"><button class="greenButton">Личный кабинет</button></a>
				<form style="display: inline;" action="/logout" method="post">
				    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
				    <input class="greenButton" type="submit" value="Выйти" />
				</form>
			<#else>
			    <a href="/login"><button class="greenButton">Вход</button></a>
				<a href="/register"><button class="greenButton">Регистрация</button></a>
			</#if>
		</div>
	</header>
</#macro>