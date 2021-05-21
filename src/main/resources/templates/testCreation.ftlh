<#import "parts/common.ftlh" as c>
<@c.page>
	<div id="content">
		<h1>Создание теста</h1>
		<div id="formDiv">
			<form method="POST" action="/create">
				<input type="hidden" name="_csrf" value="${_csrf.token}"/>
				<div class="contentRow">
					<div class="contentCell">
						<h2>Общие данные</h2>
						<@c.formField type="text" id="testName" label="Название теста" />
						<@c.formField type="text" id="testDescription" label="Описание" />
						<@c.formField type="select" id="testCategory" label="Категория" array="${categories?join(', ')}" />
					</div>
				</div>
				<div class="formField">
					<button type="submit" class="loginButton">Создать</button>
				</div>
			</form>
		</div>
	</div>
</@c.page>