import {Doughnut} from 'vue-chartjs'

export default {
  extends: Doughnut,
  props: ['data', 'labels'],
  mounted() {
    this.renderChart({
      labels: this.labels,
      datasets: [
        {
          backgroundColor: [
            '#4dc9f6',
            '#f67019',
            '#f53794',
            '#537bc4',
            '#acc236',
            '#166a8f',
            '#00a950',
            '#58595b',
            '#8549ba'
          ],
          data: this.data
        }
      ]
    }, {
      legend: {
        labels: {
          usePointStyle: true,
        }
      },
      responsive: true, maintainAspectRatio: false, circumference: Math.PI, rotation: -Math.PI
    })
  }
}
