<#include "common/macro.ftl">

<@commonHead/>
<div id="app">

    <div id="top" style="background: #d8f0ff;padding:5px;overflow:hidden;">
        <el-button type="primary" icon="el-icon-search" @click="search" style="color:white">查询</el-button>
        <el-button type="danger" icon="el-icon-circle-plus-outline" @click="handleAdd" style="color:white">添加账户</el-button>
        </span>
    </div>

    <br/>

    <div style="margin-top:15px">

        <el-table ref="testTable" :data="tableData" style="width:100%" border>

            <el-table-column prop="accountId" label="账户编号" sortable show-overflow-tooltip></el-table-column>

            <el-table-column prop="accountName" label="账户名" sortable show-overflow-tooltip></el-table-column>

            <el-table-column prop="accountSeq" label="账户更新序列号" sortable show-overflow-tooltip></el-table-column>

            <el-table-column prop="subjectNo" label="科目编号" sortable show-overflow-tooltip></el-table-column>

            <el-table-column prop="subjectType" label="科目类型" sortable show-overflow-tooltip></el-table-column>

            <el-table-column prop="accountState" label="账户状态" sortable show-overflow-tooltip></el-table-column>

            <el-table-column prop="cashAmount" label="账户余额" sortable show-overflow-tooltip></el-table-column>

            <el-table-column prop="createTime"
                             label="创建时间"
                             :formatter="setMoment"
                             sortable show-overflow-tooltip></el-table-column>
            <el-table-column prop="updateTime"
                             label="更新时间"
                             :formatter="setMoment"
                             sortable show-overflow-tooltip></el-table-column>
            <el-table-column prop="sign" label="签名" sortable show-overflow-tooltip></el-table-column>

            <el-table-column prop="custId" label="custId" sortable show-overflow-tooltip></el-table-column>

            <el-table-column label="操作" width="310" >

                <template scope="scope">

                    <el-button size="small" type="warning" @click="handlePause(scope.$index, scope.row)">冻结</el-button>

                    <el-button size="small" type="info" @click="handleResume(scope.$index, scope.row)">恢复</el-button>

                    <el-button size="small" type="danger" icon="el-icon-delete" @click="handleDelete(scope.$index, scope.row)">删除</el-button>

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

    <el-dialog title="添加账户" :visible.syn="dialogFormVisible" @close="dialogFormVisible = false">

        <el-form :model="form">

            <el-form-item label=”账户编号" label-width="120px" style="width:60%">

                <el-input v-model="form.accountId" auto-complete="off"></el-input>

            </el-form-item>

            <el-form-item label="账户名" label-width="120px" style="width:60%">

                <el-input v-model="form.accountName" auto-complete="off"></el-input>

            </el-form-item>

            <el-form-item label="科目编号" label-width="120px" style="width:60%">

                <el-input v-model="form.subjectNo" auto-complete="off"></el-input>

            </el-form-item>

            <el-form-item label="科目类型" label-width="120px" style="width:60%">

                <el-input v-model="form.subjectType" auto-complete="off"></el-input>

            </el-form-item>

            <el-form-item label="账户状态" label-width="120px" style="width:60%">

                <el-input v-model="form.accountState" auto-complete="off"></el-input>

            </el-form-item>
            <el-form-item label="账户余额" label-width="120px" style="width:60%">

                <el-input v-model="form.cashAmount" auto-complete="off"></el-input>

            </el-form-item>
            <el-form-item label="借贷标识" label-width="120px" style="width:60%">

                <el-input v-model="form.drcrFlag" auto-complete="off"></el-input>

            </el-form-item>

            <el-form-item label="借记发生额" label-width="120px" style="width:60%">

                <el-input v-model="form.drAmount" auto-complete="off"></el-input>

            </el-form-item>

            <el-form-item label="贷记发生额" label-width="120px" style="width:60%">

                <el-input v-model="form.crAmount" auto-complete="off"></el-input>

            </el-form-item>

            <el-form-item label="签名" label-width="120px" style="width:60%">

                <el-input v-model="form.sign" auto-complete="off"></el-input>

            </el-form-item>
            <el-form-item label="cust_id" label-width="120px" style="width:60%">

                <el-input v-model="form.custId" auto-complete="off"></el-input>

            </el-form-item>

        </el-form>

        <div slot="footer" class="dialog-footer">

            <el-button type="danger" icon="el-icon-close" @click="dialogFormVisible = false">取 消</el-button>

            <el-button type="success" icon="el-icon-check" @click="add">确 定</el-button>

        </div>

    </el-dialog>

    <el-dialog title="修改账户" :visible.syn="updateFormVisible" @close="updateFormVisible = false">

        <el-form :model="upForm">

            <el-form-item label=”账户编号" label-width="120px" style="width:60%">

                <el-input v-model="upForm.accountId" auto-complete="off"></el-input>

            </el-form-item>

            <el-form-item label="账户名" label-width="120px" style="width:60%">

                <el-input v-model="upForm.accountName" auto-complete="off"></el-input>

            </el-form-item>

            <el-form-item label="科目编号" label-width="120px" style="width:60%">

                <el-input v-model="upForm.subjectNo" auto-complete="off"></el-input>

            </el-form-item>

            <el-form-item label="科目类型" label-width="120px" style="width:60%">

                <el-input v-model="upForm.subjectType" auto-complete="off"></el-input>

            </el-form-item>

            <el-form-item label="账户状态" label-width="120px" style="width:60%">

                <el-input v-model="upForm.accountState" auto-complete="off"></el-input>

            </el-form-item>
            <el-form-item label="账户余额" label-width="120px" style="width:60%">

                <el-input v-model="upForm.cashAmount" auto-complete="off"></el-input>

            </el-form-item>
            <el-form-item label="借贷标识" label-width="120px" style="width:60%">

                <el-input v-model="upForm.drcrFlag" auto-complete="off"></el-input>

            </el-form-item>

            <el-form-item label="借记发生额" label-width="120px" style="width:60%">

                <el-input v-model="upForm.drAmount" auto-complete="off"></el-input>

            </el-form-item>

            <el-form-item label="贷记发生额" label-width="120px" style="width:60%">

                <el-input v-model="form.crAmount" auto-complete="off"></el-input>

            </el-form-item>

            <el-form-item label="签名" label-width="120px" style="width:60%">

                <el-input v-model="upForm.sign" auto-complete="off"></el-input>

            </el-form-item>
            <el-form-item label="cust_id" label-width="120px" style="width:60%">

                <el-input v-model="upForm.custId" auto-complete="off"></el-input>

            </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">

            <el-button type="danger" icon="el-icon-close" @click="updateFormVisible = false">取 消</el-button>

            <el-button type="success" icon="el-icon-check" @click="update">确 定</el-button>

        </div>

    </el-dialog>

    <el-dialog title="冻结账户" :visible.syn="pauseFromVisible" @close="pauseFromVisible = false">
        <div style="text-align: center">您确定要冻结账户：{{upFrom.accountId}} 吗?</div>
        <div slot="footer" class="dialog-footer">

            <el-button type="danger" icon="el-icon-close" @click="pauseFromVisible = false">取 消</el-button>

            <el-button type="success" icon="el-icon-check" @click="pause">确 定</el-button>

        </div>
    </el-dialog>

    <el-dialog title="恢复账户" :visible.syn="resumeFromVisible" @close="resumeFromVisible = false">

        <div style="text-align: center">您确定要恢复账户：{{upFrom.accountId}} 吗?</div>

        <div slot="footer" class="dialog-footer">

            <el-button type="danger" icon="el-icon-close" @click="resumeFromVisible = false">取 消</el-button>

            <el-button type="success" icon="el-icon-check" @click="resume">确 定</el-button>

        </div>
    </el-dialog>

    <el-dialog title="删除账户" :visible.syn="deleteFromVisible" @close="deleteFromVisible = false">

        <div style="text-align: center">您确定要删除账户：{{upFrom.accountId}} 吗?</div>

        <div slot="footer" class="dialog-footer">

            <el-button type="danger" icon="el-icon-close" @click="deleteFromVisible = false">取 消</el-button>

            <el-button type="success" icon="el-icon-check" @click="deleteOne">确 定</el-button>

        </div>
    </el-dialog>

</div>

<footer align="center">
    <p>&copy; 账户管理</p>
</footer>

<script>
    var vue = new Vue({
        el:"#app",
        data: {
            //表格当前页数据
            tableData: [],

            //请求的URL
            url:'account/page',

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
            form:{
                accountId:'',
                accountName:'',
                accountSeq:'',
                subjectNo:'',
                subjectType:'',
                accountState:'',
                cashAmount:'',
                drcrFlag:'',
                drAmount:'',
                crAmount:'',
                sign:'',
                custId:''
            },

            upForm:{
                accountId:'',
                accountName:'',
                accountSeq:'',
                subjectNo:'',
                subjectType:'',
                accountState:'',
                cashAmount:'',
                drcrFlag:'',
                drAmount:'',
                crAmount:'',
                sign:'',
                custId:''
            }

        },

        methods: {

            //从服务器读取数据
            loadData: function(pageNum, pageSize){
                this.$http.get('account/page?' + 'pageNum=' +  pageNum + '&pageSize=' + pageSize).then(function(res){
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
                this.$http.post('account/add',this.form,{emulateJSON: true}).then(function(res){
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


            setMoment: function (row, column) {
                var time=row[column.property];
                if (time != null) {
                    return moment(time).format('YYYY-MM-DD HH:mm:ss');
                } else {
                    return time;
                }
            },

            //弹出冻结账户添加对话框
            handlePause: function(index, row){
                console.log(row.triggerState);
                if(row.accountState!='FROZEN'){
                    return;
                }
                this.pauseFromVisible=true;
                this.upForm.accountId = row.accountId;
            },

            //冻结账户
            pause:function () {
                this.$http.post('account/frozen/'+this.upForm.accountId).then(function(res){
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


            //弹出解冻账户对话框
            handleResume: function(index, row){
                console.log(row.triggerState);
                if(!(row.accountId=='FROZEN')){
                    return;
                }
                this.resumeFromVisible=true;
                this.upForm.accountId = row.accountId;
            },

            //恢复账户
            resume:function () {
                this.$http.post('account/thaw/'+this.upForm.accountId).then(function(res){

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
                this.upForm.accountId = row.accountId;
            },

            //删除账户
            deleteOne: function () {

                this.$http.delete('account/'+this.upForm.accountId).then(function(res){

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
                this.upForm.accountId = row.accountId;
                this.upForm.accountState = row.accountState;
                this.upForm.cashAmount = row.cashAmount;
                this.upForm.accountName = row.accountName;
                this.upForm.accountSeq = row.accountSeq;
                this.upForm.crAmount = row.crAmount;
                this.upForm.custId = row.custId;
                this.upForm.drAmount = row.drAmount;
                this.upForm.drcrFlag = row.drcrFlag;
                this.upForm.sign = row.sign;
                this.upForm.subjectNo = row.subjectNo;
                this.upForm.subjectType = row.subjectType;
            },

            //更新账户
            update: function(){
                this.$http.put('account/'+this.upForm.accountId,this.upForm,{emulateJSON: true}).then(function(res){

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
