<#import "parts/common.ftlh" as c>
<@c.page>
	<div id="content">
		<h1>Добавление типов заданий</h1>
		<div id="formDiv">
			<a href="/test/edit?id=${test.id}"><button class="loginButton">Вернуться</button></a>
			<#list test.groupList as group>
				<div class="profileTestDiv">
					<div class="paddingClass">
						${group.name} (${group.maxValue})
					</div>
					<div class="profileTestActionsDiv">
						<div class="paddingClass">
							<a href="/test/addresults?id=${test.id}&groupid=${group.id}" class="link">Результаты</a>
						</div>
						<div class="paddingClass">
							<a href="/test/deletegroup?id=${test.id}&groupid=${group.id}" class="link">Удалить</a>
						</div>
					</div>
				</div>
			<#else>
				<div class="profileTestDiv">
					Здесь вы можете создать тип задания
				</div>
			</#list>
			<form method="POST" action="/test/addgroups">
				<input type="hidden" name="_csrf" value="${_csrf.token}"/>
				<h2>Группа</h2>
				<input type="hidden" id="id" name="id" value="${test.id}">
				<@c.formField type="text" id="groupName" label="Название типа"/>
				<@c.formField type="text" id="maxValue" label="Макс. значение"/>
				<div class="formField">
					<button type="submit" class="loginButton">Сохранить</button>
				</div>
			</form>
		</div>
	</div>
</@c.page>