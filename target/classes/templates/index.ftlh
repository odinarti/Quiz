<#import "parts/common.ftlh" as c>
<@c.page>
	<div id="testsDiv">
		<#list testList as test>

			<div class="testDiv">
			<a href="/take/${test.id}">
				<div class="testDivImage">
					<img src="/img/${test.category.name}.svg" width="140px" height="140px" />
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
				</a>
			</div>
			<#else>
			Пока тестов нет!
		</#list>
	</div>
</@c.page>