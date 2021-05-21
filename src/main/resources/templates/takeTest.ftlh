<#import "parts/common.ftlh" as c>
<@c.page>
	<div class="questionDiv">
		<#if results??>
			<h1>Тест завершен!</h1>
			<div id="formDiv">
				<h2>Результаты</h2>
				<#list results as result>
					<div class="testDivName">${result.description}</div>
				</#list>
				<div class="formField">
					<a href="/send?id=${test.id}"><button class="loginButton">Экспортировать результаты</button></a>
					<a href="/retry?id=${test.id}"><button class="loginButton">Пройти заново</button></a>
				</div>
			</div>
		<#else>
			<div class="questionDescriptionDiv">
				<b>${quests.description}</b>
			</div>
			<form action="/take" method="POST">
				<input type="hidden" name="_csrf" value="${_csrf.token}"/>
			<#list quests.answerList as answer>
				<div class="questionAnswerDiv">
					<input type="radio" name="answerid" id="answerid" value="${answer.id}">${answer.description}
				</div>
			</#list>
			<div class="formField">
				<input type="hidden" name="id" id="id" value="${test.id}">
				<button type="submit" class="loginButton">Далее</button>
			</div>
			</form>
		</#if>
	</div>
</@c.page>