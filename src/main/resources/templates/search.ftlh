<#import "parts/common.ftlh" as c>
<@c.page>
	<div id="testsDiv">
		<div id="searchContent">
			<div id="formDiv">
				<form method="POST">
					<input type="hidden" name="_csrf" value="${_csrf.token}"/>
					<@c.formField type="text" id="query" label="Поиск" />
					<div class="formField">
						<button type="submit" class="loginButton">Поиск</button>
					</div>
				</form>
			</div>
		</div>
		<#if result??>
			<#list result as test>
			<a href="/take/${test.id}" class="aLogo">
				<div class="testDiv">
					<div class="testDivImage">
						<img src="/img/${test.category.name}.svg" width="80px" height="80px" />
						<div class="testDivDate">
							${test.creationDate}
						</div>
					</div>
					<div class="testDivCell">
						<div class="testDivName">
							${test.name}
						</div>
						<div class="testDivDescription">
							${test.description}
						</div>
					</div>
				</div>
			</a>
				<#else>
				Результатов нет!
			</#list>
		</#if>
	</div>
</@c.page>