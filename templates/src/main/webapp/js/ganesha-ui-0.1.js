function fitContent(iFrame) {
	iFrame.height(iFrame.contents().height());
	iFrame.width(iFrame.contents().width());
}

function confirmAction() {
	return confirm("Are you sure want to perform this action?");
}

function confirmCancel() {
	return confirm("If you leave this page you will lost all data that already inputed. Are you sure want to leave this page? ");
}

function stripeTable(table) {
	var tableBody = table.children("tbody");
	var rows = tableBody.children("tr");
	for ( var i = 0; i < rows.length; i++) {
		rows.eq(i).attr("class", i % 2 == 0 ? "rowOdd" : "rowEven");
	}
}

function pageFirst() {
	var pageNumber = $('#paginationForm').find('#pageNumber');
	pageNumber.val(1);
}

function pageNext() {
	var pageNumber = $('#paginationForm').find('#pageNumber');
	pageNumber.val(pageNumber.val() * 1 + 1);
}

function pagePrevious() {
	var pageNumber = $('#paginationForm').find('#pageNumber');
	pageNumber.val(pageNumber.val() * 1 - 1);
}

function pageGoto(number) {
	var pageNumber = $('#paginationForm').find('#pageNumber');
	pageNumber.val(number);
}