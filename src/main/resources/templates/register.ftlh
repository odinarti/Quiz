<#import "parts/common.ftlh" as c>
<@c.page>
		<div id="content">
			<h1>Регистрация</h1>
			<div id="formDiv">
				<form method="POST" action="/register">
					<input type="hidden" name="_csrf" value="${_csrf.token}"/>
					<@c.formField type="text" id="username" label="Логин" />
					<@c.formField type="text" id="email" label="Почта" />
					<@c.formField type="password" id="password" label="Пароль" />
					<div class="formField">
						<button class="loginButton" id="registerButton">Продолжить</button>
					</div>
				</form>
			</div>
		</div>
</@c.page>