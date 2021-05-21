<#import "parts/common.ftlh" as c>
<@c.page>
	<div id="content">
		<h1>Редактирование теста</h1>
		<div id="formDiv">
			<div class="contentRow">
				<div class="contentCell">
					<form method="POST" action="/test/edit">
						<input type="hidden" name="_csrf" value="${_csrf.token}"/>
						<h2>Общие данные</h2>
						<input type="hidden" id="id" name="id" value="${test.id}">
						<@c.formField type="text" id="testName" label="Название
						" value="${test.name}"/>
						<@c.formField type="text" id="testDescription" label="Описание" value="${test.description}"/>
						<@c.formField type="select" id="testCategory" label="Категория" array="${categories?join(', ')}" testCategory="${test.category.name}" />
						<div class="formField">
						</div>
						<div class="formField">
							<button type="submit" class="loginButton">Сохранить</button>
						</div>
					</form>
					<div class="formField">
						<a href="/test/publish?id=${test.id}"><button class="loginButton"><#if test.published>Снять с публикации<#else>Опубликовать</#if></button></a>
					</div>
					
					<h2>Приглашение пользователей</h2>
					<form method="POST" action="/test/invite">
						<input type="hidden" id="id" name="id" value="${test.id}">
						<@c.formField type="text" id="username" label="Логин"/>
						<div class="formField">
							<button type="submit" class="loginButton">Пригласить</button>
						</div>
					</form>
					
				</div>
				<div class="contentCell">
					<h2>Типы</h2>
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
							Вы можете добавить типы
						</div>
					</#list>
					<a href="/test/addgroups?id=${test.id}"><button class="loginButton">Добавить типы</button></a>
				</div>
				<div class="contentCell">
					<h2>Вопросы</h2>
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
							Вы можете добавить вопросы
						</div>
					</#list>
					<a href="/test/addquestions?id=${test.id}"><button class="loginButton">Добавить вопросы</button></a>
				</div>
			</div>
		</div>
	</div>
</@c.page>