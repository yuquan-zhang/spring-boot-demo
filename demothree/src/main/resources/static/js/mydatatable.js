var i18n = {
    "create": {
        "button": "新增",
        "title":  "创建新条目",
        "submit": "创建"
    },

    "edit": {
        "button": "编辑",
        "title":  "编辑条目",
        "submit": "更新"
    },

    "remove": {
        "button": "删除",
        "title":  "删除",
        "submit": "删除",
        "confirm": {
            "_": "你确定要删除这 %d 行吗?",
            "1": "你确定要删除这 1 行吗?"
        }
    },

    "error": {
        "system": "发生了一个系统错误 (更多信息)"
    },

    "multi": {
        "title": "Multiple values",
        "info": "The selected items contain different values for this input. To edit and set all items for this input to the same value, click or tap here, otherwise they will retain their individual values.",
        "restore": "Undo changes",
        "noMulti": "This input can be edited individually, but not part of a group."
    },

    "datetime": {
        "previous": 'Previous',
        "next":     'Next',
        "months":   [ 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December' ],
        "weekdays": [ 'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat' ],
        "amPm":     [ 'am', 'pm' ],
        "unknown":  '-'
    }
};
(function ($) {
    // 屏幕适配
    var ClientH = windowHeight();
    var offsetH = ClientH - 150;
    var defaults = {
        processing: true,
        serverSide: true,
        searching: false,
        ordering:  true,
        select: 'multiple',
        lengthMenu: [[10, 20, 50, -1], [10, 20, 50, "全部"]],
        dom: 'lfBrtip',
        language: {
            "sProcessing":   "处理中...",
            "sLengthMenu":   "显示 _MENU_ 项结果",
            "sZeroRecords":  "没有匹配结果",
            "sInfo":         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix":  "",
            "sSearch":       "搜索:",
            "sUrl":          "",
            "sEmptyTable":     "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands":  ",",
            "oPaginate": {
                "sFirst":    "首页",
                "sPrevious": "上页",
                "sNext":     "下页",
                "sLast":     "末页"
            },
            "oAria": {
                "sSortAscending":  ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            },
            "select": {
                "rows": {
                    _: "选择了 %d 行",
                    0: "点击行可选中它",
                    1: "选中了 1 行"
                }
            },
            "buttons":[
                {
                    extend: 'copy',
                    text: '复制到剪贴板'
                },
                {
                    extend: 'excel',
                    text: '导出EXCEL'
                },
                {
                    extend: 'print',
                    text: '打印'
                }
            ],
        },
    };

    var MyDataTable = function (element, options) {
        this.$this = $(element);
        this.DT = this.$this.DataTable(options);
        this.DataTable = function() {
            return this.DT;
        }

    }

    $.fn.MyDataTable = function (options) {
        return new MyDataTable(this, $.extend({},defaults,options)).DataTable();
    };

})(jQuery);
