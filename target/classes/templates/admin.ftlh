<#import "parts/common.ftlh" as c>
<@c.page>
	<div id="content">
		<h1>Панель администратора</h1>
		<div id="formDiv">
			<h2>Пользователи</h2>
			<form method="POST" action="/admin/settester">
				<@c.formField type="text" id="username" label="Назначить тестировщика" />
				<div class="formField">
					<button class="loginButton">Дать права</button>
				</div>
			</form>
		</div>
	</div>
</@c.page>