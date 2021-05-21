<#import "parts/common.ftlh" as c>
<@c.page>
	<div id="content">
		<h1>Восстановление пароля</h1>
		<div id="formDiv">
			<form method="POST" action="/changePassword">
				<input type="hidden" name="_csrf" value="${_csrf.token}"/>
				<@c.formField type="text" id="username" label="Введите логин" />
				<div class="formField">
					<button class="loginButton">Продолжить</button>
				</div>
			</form>
		</div>
	</div>
</@c.page>