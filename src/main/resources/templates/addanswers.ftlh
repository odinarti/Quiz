<#import "parts/common.ftlh" as c>
<@c.page>
	<div id="content">
		<h1>Вопрос: ${quests.description}</h1>
		<div id="formDiv">
			<a href="/test/edit?id=${test.id}"><button class="loginButton">Вернуться</button></a>
			<#list quests.answerList as answer>
				<div class="profileTestDiv">
					<div class="paddingClass">
					${answer.description} (${answer.weight} - ${answer.group.name})
					</div>
					<div class="profileTestActionsDiv">
						<div class="paddingClass">
							<a href="/test/deleteanswer?id=${test.id}&groupid=${answer.group.id}&questionid=${quests.id}&answerid=${answer.id}" class="link">Удалить</a>
						</div>
					</div>
				</div>
			<#else>
				<div class="profileTestDiv">
					Здесь вы можете создать ответы
				</div>
			</#list>
			<form method="POST" action="/test/addanswers">
				<input type="hidden" name="_csrf" value="${_csrf.token}"/>
				<h2>Ответы</h2>
				<input type="hidden" id="id" name="id" value="${test.id}">
				<input type="hidden" id="groupid" name="questionid" value="${quests.id}">
				<@c.formField type="text" id="description" label="Описание"/>
				<@c.formField type="text" id="weight" label="Значение"/>
				<@c.formField type="select" id="group" label="Тип задания" array="${test.getGroupListName()?join(', ')}" />
				<div class="formField">
					<button type="submit" class="loginButton">Сохранить</button>
				</div>
			</form>
		</div>
	</div>
</@c.page>