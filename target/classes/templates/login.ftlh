<#import "parts/common.ftlh" as c>
<@c.page>
	<div id="content">
		<h1>Вход</h1>
		<div id="formDiv">
			<#if message??>
				<div class="message">${message}</div>
			</#if>
			<form method="POST" action="/login">
			<input type="hidden" name="_csrf" value="${_csrf.token}"/>
			<@c.formField type="text" id="username" label="Логин" />
			<@c.formField type="password" id="password" label="Пароль" />
				<div class="formField">
					<a href="/recoverPassword" class="aLogo"><label>Восстановление пароля</label></a>
					<button type="submit" class="loginButton">Вход</button>
				</div>
			</form>
		</div>
	</div>
</@c.page>