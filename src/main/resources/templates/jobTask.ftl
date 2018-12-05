<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quartz Demo</title>
    <#--<link rel="stylesheet" href="https://unpkg.com/element-ui@2.0.5/lib/theme-chalk/index.css">-->
    <#--<script src="https://unpkg.com/vue/dist/vue.js"></script>-->
    <#--<script src="http://cdn.bootcss.com/vue-resource/1.3.4/vue-resource.js"></script>-->
    <#--<script src="https://unpkg.com/element-ui@2.0.5/lib/index.js"></script>-->
    <link rel="stylesheet" href="/css/index.css">
    <script src="/js/vue.js"></script>
    <script src="/js/vue-resource.js"></script>
    <script src="/js/index.js"></script>

    <style>
        #top {
            background: #d8f0ff;
            padding:5px;
            overflow:hidden
        }
    </style>

</head>
<body>
<div id="app">

    <div id="top">
        <el-button type="primary" size="small" style="color:white">查询</el-button>
        <el-button type="primary" size="small" @click="handleAdd" style="color:white">添加</el-button>
        </span>
    </div>

    <br/>

    <div style="margin-top:15px">

        <el-table ref="testTable" :data="tableData" style="width:100%" border>

            <el-table-column prop="jobName" label="任务名称" sortable show-overflow-tooltip></el-table-column>

            <el-table-column prop="jobGroup" label="任务所在组" sortable></el-table-column>

            <el-table-column prop="jobClassName" label="任务类名" sortable></el-table-column>

            <el-table-column prop="triggerName" label="触发器名称" sortable></el-table-column>

            <el-table-column prop="triggerGroup" label="触发器所在组" sortable></el-table-column>

            <el-table-column prop="cronExpression" label="表达式" sortable></el-table-column>

            <el-table-column prop="triggerState"
                             label="状态"
                             :formatter="setState"
                             sortable></el-table-column>

            <el-table-column prop="startTime"
                             label="开始时间"
                             sortable></el-table-column>

            <el-table-column prop="timeZoneId" label="时区" sortable></el-table-column>

            <el-table-column label="操作" width="300">

                <template scope="scope">

                    <el-button size="small" type="warning" @click="handlePause(scope.$index, scope.row)">暂停</el-button>

                    <el-button size="small" type="info" @click="handleResume(scope.$index, scope.row)">恢复</el-button>

                    <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>

                    <el-button size="small" type="success" @click="handleUpdate(scope.$index, scope.row)">修改</el-button>

                </template>

            </el-table-column>

        </el-table>

        <div align="center">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[10, 20, 30, 40]"
                    :page-size="pagesize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="totalCount">
            </el-pagination>
        </div>
    </div>

    <el-dialog title="添加任务" :visible.syn="dialogFormVisible">

        <el-form :model="form">

            <el-form-item label="任务名称" label-width="120px" style="width:60%">

                <el-input v-model="form.jobName" auto-complete="off"></el-input>

            </el-form-item>

            <el-form-item label="任务分组" label-width="120px" style="width:60%">

                <el-input v-model="form.jobGroup" auto-complete="off"></el-input>

            </el-form-item>

            <el-form-item label="表达式" label-width="120px" style="width:60%">

                <el-input v-model="form.cronExpression" auto-complete="off"></el-input>

            </el-form-item>

        </el-form>

        <div slot="footer" class="dialog-footer">

            <el-button @click="dialogFormVisible = false">取 消</el-button>

            <el-button type="primary" @click="add">确 定</el-button>

        </div>
    </el-dialog>

    <el-dialog title="修改任务" :visible.syn="updateFormVisible">
        <el-form :model="jobForm">

            <el-form-item label="表达式" label-width="120px" style="width:60%">

                <el-input v-model="jobForm.cronExpression" auto-complete="on"></el-input>

            </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">

            <el-button @click="updateFormVisible = false">取 消</el-button>

            <el-button type="primary" @click="update">确 定</el-button>

        </div>

    </el-dialog>

    <el-dialog title="暂停任务" :visible.syn="pauseFromVisible">
            <div style="text-align: center">您确定要暂停任务：{{jobForm.jobClassName}} 吗?</div>
        <div slot="footer" class="dialog-footer">

            <el-button @click="pauseFromVisible = false">取 消</el-button>

            <el-button type="primary" @click="pause">确 定</el-button>

        </div>
    </el-dialog>

    <el-dialog title="恢复任务" :visible.syn="resumeFromVisible">

        <div style="text-align: center">您确定要恢复任务：{{jobForm.jobClassName}} 吗?</div>

        <div slot="footer" class="dialog-footer">

            <el-button @click="resumeFromVisible = false">取 消</el-button>

            <el-button type="primary" @click="resume">确 定</el-button>

        </div>
    </el-dialog>

    <el-dialog title="删除任务" :visible.syn="deleteFromVisible">

        <div style="text-align: center">您确定要删除任务：{{jobForm.jobClassName}} 吗?</div>

        <div slot="footer" class="dialog-footer">

            <el-button @click="deleteFromVisible = false">取 消</el-button>

            <el-button type="primary" @click="deleteOne">确 定</el-button>

        </div>
    </el-dialog>

</div>

<footer align="center">
    <p>&copy; Quartz 任务管理</p>
</footer>

<script>
    var vue = new Vue({
        el:"#app",
        data: {
            //表格当前页数据
            tableData: [],

            //请求的URL
            url:'job/queryJob',

            //默认每页数据量
            pagesize: 10,

            //当前页码
            currentPage: 1,

            //查询的页码
            start: 1,

            //默认数据总数
            totalCount: 1000,

            //添加对话框默认可见性
            dialogFormVisible: false,

            //修改对话框默认可见性
            updateFormVisible: false,

            //暂停对话框默认可见性
            pauseFromVisible: false,

            //暂停对话框默认可见性
            resumeFromVisible: false,

            //删除对话框默认可见性
            deleteFromVisible: false,

            //提交的表单
            form: {
                jobName: '',
                jobGroup: '',
                cronExpression: ''
            },
            jobForm:{
                jobClassName:'',
                jobGroupName:'',
                cronExpression:''
            }
        },

        methods: {

            //从服务器读取数据
            loadData: function(pageNum, pageSize){
                this.$http.get('job/queryJob?' + 'pageNum=' +  pageNum + '&pageSize=' + pageSize).then(function(res){
                    this.tableData = res.body.data.list;
                    this.totalCount = res.body.number;
                },function(){
                    console.log('failed');
                });
            },

            //弹出添加对话框
            handleAdd: function(){
                this.dialogFormVisible = true;
            },

            //添加
            add: function(){
                this.$http.post('job/addJob',{"jobClassName":this.form.jobName,"jobGroupName":this.form.jobGroup,"cronExpression":this.form.cronExpression},{emulateJSON: true}).then(function(res){
                    if(res.body.code==200){
                        this.loadData(this.currentPage, this.pagesize);
                        this.dialogFormVisible = false;
                    }else{
                        console.log(res.body.msg);
                    }
                },function(){
                    console.log('failed');
                });
            },

            setState: function (row, column) {
                var state=row[column.property];
                return state=="PAUSED"?"暂停":"等待";
            },

            //弹出暂停任务添加对话框
            handlePause: function(index, row){
                console.log(row.triggerState);
                if(row.triggerState=='PAUSED'){
                    return;
                }
                this.pauseFromVisible=true;
                this.jobForm.jobClassName = row.jobName;
                this.jobForm.jobGroupName = row.jobGroup;
            },

            //暂停任务
            pause:function () {
                this.$http.post('job/pauseJob',this.jobForm,{emulateJSON: true}).then(function(res){
                    if(res.body.code==200){
                        this.pauseFromVisible=false;
                        this.loadData( this.currentPage, this.pagesize);
                    }else{
                        console.log(res.body.msg);
                    }
                },function(){
                    console.log('failed');
                });

            },


            //弹出暂停任务添加对话框
            handleResume: function(index, row){
                console.log(row.triggerState);
                if(row.triggerState=='WAITING'){
                    return;
                }
                this.resumeFromVisible=true;
                this.jobForm.jobClassName = row.jobName;
                this.jobForm.jobGroupName = row.jobGroup;
            },

            //恢复任务
            resume:function () {
                this.$http.post('job/resumeJob',this.jobForm,{emulateJSON: true}).then(function(res){

                    if(res.body.code==200){

                        this.loadData( this.currentPage, this.pagesize);
                        this.resumeFromVisible=false;

                    }else{

                        console.log(res.body.msg);

                    }

                },function(){

                    console.log('failed');

                });
            },

            //单行删除
            handleDelete: function(index, row) {

                this.deleteFromVisible=true;
                this.jobForm.jobClassName = row.jobName;
                this.jobForm.jobGroupName = row.jobGroup;

            },

            //删除任务
            deleteOne: function () {

                this.$http.post('job/deleteJob',this.jobForm,{emulateJSON: true}).then(function(res){

                    if(res.body.code==200){

                        this.loadData( this.currentPage, this.pagesize);

                        this.deleteFromVisible=false;

                    }else{

                        console.log(res.body.msg);

                    }

                },function(){

                    console.log('failed');

                });
            },

            //更新
            handleUpdate: function(index, row){
                this.updateFormVisible = true;
                this.jobForm.jobClassName = row.jobName;
                this.jobForm.jobGroupName = row.jobGroup;
                this.jobForm.cronExpression=row.cronExpression;
            },

            //更新任务
            update: function(){
                this.$http.post('job/rescheduleJob',this.jobForm,{emulateJSON: true}).then(function(res){

                    if(res.body.code==200){
                        this.loadData(this.currentPage, this.pagesize);
                        this.updateFormVisible = false;
                    }else{
                        console.log(res.body.msg);
                    }

                },function(){
                    console.log('failed');
                });

            },

            //搜索
            search: function(){

                this.loadData(this.currentPage, this.pagesize);

            },

            //每页显示数据量变更
            handleSizeChange: function(val) {

                this.pagesize = val;
                this.loadData(this.currentPage, this.pagesize);

            },

            //页码变更
            handleCurrentChange: function(val) {

                this.currentPage = val;
                this.loadData(this.currentPage, this.pagesize);

            }

        }


    });

    //载入数据
    vue.loadData(vue.currentPage, vue.pagesize);
</script>

</body>
</html>
