<#import "parts/common.ftlh" as c>
<@c.page>
	<div id="content">
		<h1>Смена пароля</h1>
		<div id="formDiv">
			<form method="POST" action="/recoverPassword">
				<input type="hidden" name="_csrf" value="${_csrf.token}"/>
				<input type="hidden" id="activationCode" name="activationCode" value="${activationCode}">
				<@c.formField type="password" id="newPassword" label="Новый пароль" />
				<div class="formField">
					<button class="loginButton" id="registerButton">Продолжить</button>
				</div>
			</form>
		</div>
	</div>
</@c.page>