### 说明内容

#### quartz 中集群如何工作


#### quartz集群类型
水平集群：
垂直集群：


#### 问题：
1.修改定时任务配置,怎么生效?
2.怎么标识是那台机器的实例?

#### quartz集群缺陷及解决：
1、时间规则更改不方便，需同步更改数据库时间规则描述
2、Quartz集群当所有节点跑在同一台服务器上，当服务器崩溃时所有节点将终止，定时任务将不能正常执行
3、Quartz集群当节点不在同一台服务器上时，因时钟的可能不同步导致节点对其他节点状态的产生影响。


#### 参考技术资料：
http://sundoctor.iteye.com/blog/486055?page=2