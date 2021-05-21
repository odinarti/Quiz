<#import "parts/common.ftlh" as c>
<@c.page>
	<div id="content">
		<h1>Добавление вопросов</h1>
		<div id="formDiv">
			<a href="/test/edit?id=${test.id}"><button class="loginButton">Вернуться</button></a>
			<#list test.questsList as quests>
				<div class="profileTestDiv">
					<div class="paddingClass">
						${quests.description}
					</div>
					<div class="profileTestActionsDiv">
						<div class="paddingClass">
							<a href="/test/addanswers?id=${test.id}&questionid=${quests.id}" class="link">Ответы</a>
						</div>
						<div class="paddingClass">
							<a href="/test/deletequestion?id=${test.id}&questionid=${quests.id}" class="link">Удалить</a>
						</div>
					</div>
				</div>
			<#else>
				<div class="profileTestDiv">
					Здесь вы можете создать вопросы
				</div>
			</#list>
			<form method="POST" action="/test/addquestions">
				<input type="hidden" name="_csrf" value="${_csrf.token}"/>
				<h2>Вопрос</h2>
				<input type="hidden" id="id" name="id" value="${test.id}">
				<@c.formField type="text" id="description" label="Описание"/>
				<div class="formField">
					<button type="submit" class="loginButton">Сохранить</button>
				</div>
			</form>
		</div>
	</div>
</@c.page>