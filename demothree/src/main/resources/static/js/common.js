/**
 * 获取窗体可见去区域的高度
 * @returns {number}
 */
function windowHeight() {
    var myHeight = 0;
    if (typeof(window.innerHeight) === 'number') {
        //Non-IE
        myHeight = window.innerHeight;
    } else if (document.documentElement && (document.documentElement.clientHeight)) {
        //IE 6+ in 'standards compliant mode'
        myHeight = document.documentElement.clientHeight;
    } else if (document.body && (document.body.clientHeight)) {
        //IE 4 compatible
        myHeight = document.body.clientHeight;
    }
    return myHeight;
}


/**
 * 对Date的扩展，将 Date 转化为指定格式的String
 * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 * 例子：
 * (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 * (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
 * @param fmt
 * @returns {*}
 * @constructor
 */
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

//序列化form表单
jQuery.fn.uiSerializeForm = function () {
    var o = {};
    var a = this.serializeArray();
    jQuery.each(a, function (index, li) {
        if (o[li.name]) {
            o[li.name] = o[li.name] + "," + li.value;
        } else {
            o[li.name] = li.value;
        }
    });
    return o;
};

//反序列化表单
jQuery.fn.uiDeSerializeForm = function (obj) {
    var a = this.serializeArray();
    $.each(a,function(index,li){
        $("[name='"+li.name+"']").val(eval("obj."+li.name));
    })
};

// 校验信息中文提示
jQuery.extend(jQuery.validator.messages, {
    required: "必填字段",
    remote: "请修正该字段",
    email: "请输入正确格式的电子邮件",
    url: "请输入合法的网址",
    date: "请输入合法的日期",
    dateISO: "请输入合法的日期 (ISO).",
    number: "请输入合法的数字",
    digits: "只能输入整数",
    creditcard: "请输入合法的信用卡号",
    equalTo: "请再次输入相同的值",
    accept: "请输入拥有合法后缀名的字符串",
    maxlength: jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串"),
    minlength: jQuery.validator.format("请输入一个 长度最少是 {0} 的字符串"),
    rangelength: jQuery.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"),
    range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
    max: jQuery.validator.format("请输入一个最大为{0} 的值"),
    min: jQuery.validator.format("请输入一个最小为{0} 的值")
});

/**
 * 关闭当前layer插件打开的iframe弹窗
 */
function closeLayerIframe() {
    var index = window.parent.layer.getFrameIndex(window.name);
    window.parent.layer.close(index);
    return false;
}

function bindAll() {
    //关闭layer的iframe弹窗 绑定到有class为".cancel-layer"的标签
    $(".cancel-layer").on("click",function(){
        return closeLayerIframe();
    })

}

jQuery(function () {
    bindAll();
});

function isNull(value){
    return value == null || value === "" || value === undefined || value === "undefined";
}