<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">
<@c.page>
	<div id="content">
		<h1>Личный кабинет</h1>
		<div id="formDiv">
		
			<h2>Сведение о профиле</h2>
			<@c.labelField name="username" label="Логин" value="${username}"/>
			<@c.labelField name="email" label="Почта" value="${user.email}"/>
			<@c.labelField name="roles" label="Роль" value="" />
			
			<h2>Смена пароля</h2>
			<div class="formField">
				<a href="/changepassword?username=${user.username}"><button class="loginButton">Изменить пароль</button></a>
			</div>
			
			<h2>Завершенные тесты</h2>
			<#list completedTests as taked>
			<div class="profileTestDiv">
			 	${taked.test.name}
			 	<div class="profileTestActionsDiv">
					<a href="/send?id=${taked.test.id}" class="link">Экспортировать результат</a>
					<a href="/retry?id=${taked.test.id}" class="link">Пройти заново</a>
				</div>
			 </div>
			 <#else>
			 	Нет завершённых тестов
			</#list>
			
			<h2>Приглашения</h2>
			<#list invites as invite>
			<div class="profileTestDiv">
			 	${invite.test.name}
			 	<div class="profileTestActionsDiv">
					<a href="/take/${invite.test.id}" class="link">Пройти</a>
					<a href="/closeInvite?id=${invite.test.id}" class="link">Отказаться</a>
				</div>
			 </div>
			 <#else>
			 	Нет приглашений
			</#list>
			
			<#if roles?? && roles?seq_contains("ROLE_TESTER")>
			<h2>Ваши тесты</h2>
				<#list tests as test>
					<div class="profileTestDiv">
						${test.name}
						<div class="profileTestActionsDiv">
							<a href="/test/edit?id=${test.id}" class="link">Редактировать</a>
							<a href="/test/delete?id=${test.id}" class="link">Удалить</a>
						</div>
					</div>
				<#else>
					Нет созданных тестов
				</#list>
			</#if>
		</div>
	</div>
</@c.page>