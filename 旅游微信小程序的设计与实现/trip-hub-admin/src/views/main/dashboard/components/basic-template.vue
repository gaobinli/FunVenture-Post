<template>
  <div class="dashboard-container">
    <!-- 标题 -->
    <el-card class="title-card">
      <h1>重庆旅游 - 数据概览</h1>
      <p class="subtitle">实时监控系统数据指标</p>
    </el-card>

    <!-- 数据统计卡片 -->
    <div class="stats-grid">
      <el-card class="stat-card">
        <div class="stat-content">
          <div class="stat-value">{{ stats.totalUsers }}</div>
          <div class="stat-label">用户总数</div>
          <div class="stat-icon users-icon">👥</div>
        </div>
      </el-card>

      <el-card class="stat-card">
        <div class="stat-content">
          <div class="stat-value">{{ stats.totalOrders }}</div>
          <div class="stat-label">订单总数</div>
          <div class="stat-icon orders-icon">📦</div>
        </div>
      </el-card>

      <el-card class="stat-card">
        <div class="stat-content">
          <div class="stat-value">{{ stats.totalScenicSpots }}</div>
          <div class="stat-label">景点数量</div>
          <div class="stat-icon scenic-icon">🏔️</div>
        </div>
      </el-card>

      <el-card class="stat-card">
        <div class="stat-content">
          <div class="stat-value">{{ stats.totalFoods }}</div>
          <div class="stat-label">美食数量</div>
          <div class="stat-icon food-icon">🍽️</div>
        </div>
      </el-card>

      <el-card class="stat-card">
        <div class="stat-content">
          <div class="stat-value">{{ stats.totalHotels }}</div>
          <div class="stat-label">酒店数量</div>
          <div class="stat-icon hotel-icon">🏨</div>
        </div>
      </el-card>

    </div>

    <!-- 图表区域 -->
    <div class="charts-grid">
      <!-- 左侧：订单类型分布和月度订单趋势 -->
      <div class="chart-section left-section">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>订单类型分布</span>
            </div>
          </template>
          <div ref="orderTypeChart" class="chart-container"></div>
        </el-card>

        <el-card>
          <template #header>
            <div class="card-header">
              <span>月度订单趋势</span>
            </div>
          </template>
          <div ref="orderTrendChart" class="chart-container"></div>
        </el-card>
      </div>

      <!-- 右侧：订单状态分布和产品销售排行 -->
      <div class="chart-section right-section">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>订单状态分布</span>
            </div>
          </template>
          <div ref="orderStatusChart" class="chart-container"></div>
        </el-card>

        <el-card>
          <template #header>
            <div class="card-header">
              <span>产品销售排行</span>
            </div>
          </template>
          <div ref="productSalesChart" class="chart-container"></div>
        </el-card>
      </div>
    </div>

  </div>
</template>

<script lang="ts">
import { defineComponent, ref, reactive, onMounted } from 'vue'
import * as echarts from 'echarts'
import { dataList as getOrders } from '@/api/back/order'
import { dataList as getUsers } from '@/api/back/user'
import { dataList as getScenicSpots } from '@/api/back/scenicSpot'
import { dataList as getFoods } from '@/api/back/food'
import { dataList as getHotels } from '@/api/back/hotel'

interface StatData {
  totalUsers: number
  totalOrders: number
  totalScenicSpots: number
  totalFoods: number
  totalHotels: number
  totalRevenue: number
}

export default defineComponent({
  setup() {
    // 数据统计
    const stats = reactive<StatData>({
      totalUsers: 0,
      totalOrders: 0,
      totalScenicSpots: 0,
      totalFoods: 0,
      totalHotels: 0,
      totalRevenue: 0
    })

    // 库存预警数据
    const inventoryWarnings = ref<any[]>([])

    // 图表引用
    const orderTypeChart = ref<HTMLElement>()
    const orderTrendChart = ref<HTMLElement>()
    const orderStatusChart = ref<HTMLElement>()
    const productSalesChart = ref<HTMLElement>()

    // 初始化数据
    const initData = async () => {
      try {
        // 获取用户总数
        const userRes = await getUsers({ pageSize: 1, currentPage: 1 })
        stats.totalUsers = userRes.data.totalCount || 0

        // 获取订单数据
        const orderRes = await getOrders({ pageSize: 10000, currentPage: 1 })
        const orders = orderRes.data.list || []
        stats.totalOrders = orderRes.data.totalCount || 0

        // 计算总收入
        stats.totalRevenue = orders.reduce((sum: number, order: any) => {
          return sum + (order.totalPrice || 0)
        }, 0)

        // 获取景点总数
        const scenicRes = await getScenicSpots({ pageSize: 1, currentPage: 1 })
        stats.totalScenicSpots = scenicRes.data.totalCount || 0

        // 获取美食总数
        const foodRes = await getFoods({ pageSize: 1, currentPage: 1 })
        stats.totalFoods = foodRes.data.totalCount || 0

        // 获取酒店总数
        const hotelRes = await getHotels({ pageSize: 1, currentPage: 1 })
        stats.totalHotels = hotelRes.data.totalCount || 0

        // 生成库存预警数据
        generateInventoryWarnings(orders)

        // 初始化图表
        setTimeout(() => {
          initCharts(orders)
        }, 300)
      } catch (error) {
        console.error('获取数据失败:', error)
      }
    }

    // 生成库存预警数据
    const generateInventoryWarnings = (orders: any[]) => {
      const warnings: any[] = []

      // 模拟库存预警数据 (实际应从后端获取)
      if (orders.length > 0) {
        orders.forEach((order, index) => {
          if ((order.inventory || 0) < 10) {
            warnings.push({
              productName: order.productName || `产品${index}`,
              productType: ['景点', '美食', '酒店'][order.type - 1] || '未知',
              inventory: order.inventory || 0,
              address: order.address || '未知'
            })
          }
        })
      }

      inventoryWarnings.value = warnings.slice(0, 10)
    }

    // 初始化图表
    const initCharts = (orders: any[]) => {
      // 订单类型分布 (饼图)
      if (orderTypeChart.value) {
        const typeChart = echarts.init(orderTypeChart.value)
        const typeData = [
          { value: 0, name: '景点订单' },
          { value: 0, name: '美食订单' },
          { value: 0, name: '酒店订单' }
        ]

        orders.forEach((order: any) => {
          const type = order.type || 1
          if (type === 1) typeData[0].value++
          else if (type === 2) typeData[1].value++
          else if (type === 3) typeData[2].value++
        })

        typeChart.setOption({
          tooltip: {
            trigger: 'item'
          },
          legend: {
            orient: 'vertical',
            left: 'left'
          },
          series: [
            {
              name: '订单类型',
              type: 'pie',
              radius: '50%',
              data: typeData,
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        })
      }

      // 订单状态分布 (柱状图)
      if (orderStatusChart.value) {
        const statusChart = echarts.init(orderStatusChart.value)
        const statusData = [0, 0, 0, 0, 0]

        orders.forEach((order: any) => {
          const status = (order.status || 1) - 1
          if (status >= 0 && status < 5) statusData[status]++
        })

        statusChart.setOption({
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          xAxis: {
            type: 'category',
            data: ['待发货', '待收货', '已收货', '退货中', '已退货']
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              data: statusData,
              type: 'bar',
              itemStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: '#83bff6' },
                  { offset: 0.5, color: '#188df0' },
                  { offset: 1, color: '#188df0' }
                ])
              }
            }
          ]
        })
      }

      // 月度订单趋势 (折线图)
      if (orderTrendChart.value) {
        const trendChart = echarts.init(orderTrendChart.value)
        const monthData = Array(12).fill(0)

        orders.forEach((order: any) => {
          if (order.createTime) {
            const month = new Date(order.createTime).getMonth()
            monthData[month]++
          }
        })

        trendChart.setOption({
          tooltip: {
            trigger: 'axis'
          },
          xAxis: {
            type: 'category',
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              data: monthData,
              type: 'line',
              smooth: true,
              itemStyle: {
                color: '#00ccde'
              },
              areaStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: 'rgba(0, 204, 222, 0.3)' },
                  { offset: 1, color: 'rgba(0, 204, 222, 0.1)' }
                ])
              }
            }
          ]
        })
      }

      // 产品销售排行 (水平柱状图)
      if (productSalesChart.value) {
        const salesChart = echarts.init(productSalesChart.value)
        const productSales: { [key: string]: number } = {}

        orders.forEach((order: any) => {
          const name = order.name || '未知产品'
          productSales[name] = (productSales[name] || 0) + 1
        })

        const sortedProducts = Object.entries(productSales)
          .sort((a, b) => b[1] - a[1])
          .slice(0, 10)

        salesChart.setOption({
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          xAxis: {
            type: 'value'
          },
          yAxis: {
            type: 'category',
            data: sortedProducts.map(p => p[0])
          },
          series: [
            {
              data: sortedProducts.map(p => p[1]),
              type: 'bar',
              itemStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                  { offset: 0, color: '#83bff6' },
                  { offset: 0.5, color: '#188df0' },
                  { offset: 1, color: '#188df0' }
                ])
              }
            }
          ]
        })
      }
    }

    onMounted(() => {
      initData()
    })

    return {
      stats,
      inventoryWarnings,
      orderTypeChart,
      orderTrendChart,
      orderStatusChart,
      productSalesChart
    }
  }
})
</script>

<style lang="scss" scoped>
.dashboard-container {
  padding: 20px;
  background-color: #f5f7fa;

  .title-card {
    margin-bottom: 20px;

    h1 {
      margin: 0;
      font-size: 28px;
      color: #333;
      font-weight: bold;
    }

    .subtitle {
      margin: 8px 0 0 0;
      color: #909399;
      font-size: 14px;
    }
  }

  // 统计卡片网格
  .stats-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: 20px;
    margin-bottom: 30px;

    .stat-card {
      border: none;
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
      transition: all 0.3s ease;
      cursor: pointer;

      &:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
      }

      .stat-content {
        position: relative;
        padding: 20px;
        text-align: center;
        min-height: 120px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;

        .stat-value {
          font-size: 32px;
          font-weight: bold;
          color: #333;
          margin-bottom: 8px;
        }

        .stat-label {
          font-size: 14px;
          color: #909399;
        }

        .stat-icon {
          position: absolute;
          top: 15px;
          right: 20px;
          font-size: 32px;
          opacity: 0.3;
        }
      }
    }
  }

  // 图表区域
  .charts-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 20px;
    margin-bottom: 30px;

    .chart-section {
      display: flex;
      flex-direction: column;
      gap: 20px;

      :deep(.el-card) {
        box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
        border: none;

        .card-header {
          font-weight: bold;
          color: #333;
          font-size: 16px;
        }
      }
    }

    .chart-container {
      width: 100%;
      height: 300px;
    }
  }

  // 库存预警卡片
  .inventory-card {
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    border: none;

    .card-header {
      font-weight: bold;
      color: #333;
      font-size: 16px;
    }
  }
}

// 响应式设计
@media (max-width: 1200px) {
  .dashboard-container {
    .charts-grid {
      grid-template-columns: 1fr;
    }

    .stats-grid {
      grid-template-columns: repeat(3, 1fr);
    }
  }
}

@media (max-width: 768px) {
  .dashboard-container {
    padding: 10px;

    .stats-grid {
      grid-template-columns: repeat(2, 1fr);
      gap: 10px;
    }

    .title-card h1 {
      font-size: 20px;
    }
  }
}
</style>