<#import "parts/common.ftlh" as c>
<@c.page>
	<div id="content">
		<h1>Тип: ${group.name}</h1>
		<div id="formDiv">
			<a href="/test/edit?id=${test.id}"><button class="loginButton">Назад</button></a>
			<#list group.resultList as result>
				<div class="profileTestDiv">
					<div class="paddingClass">
						${result.description} (${result.lowerBezel}-${result.highBezel})
					</div>
					<div class="profileTestActionsDiv">
						<div class="paddingClass">
							<a href="/test/deleteresult?id=${test.id}&groupid=${group.id}&resultid=${result.id}" class="link">Удалить</a>
						</div>
					</div>
				</div>
			<#else>
				<div class="profileTestDiv">
					Здесь вы можете добавить интерпритацию результатов
				</div>
			</#list>
			<form method="POST" action="/test/addresults">
				<input type="hidden" name="_csrf" value="${_csrf.token}"/>
				<h2>Результаты</h2>
				<input type="hidden" id="id" name="id" value="${test.id}">
				<input type="hidden" id="groupid" name="groupid" value="${group.id}">
				<@c.formField type="text" id="description" label="Описание"/>
				<@c.formField type="text" id="lowerBezel" label="Нижний ценз"/>
				<@c.formField type="text" id="highBezel" label="Верхний ценз"/>
				<div class="formField">
					<button type="submit" class="loginButton">Сохранить</button>
				</div>
			</form>
		</div>
	</div>
</@c.page>