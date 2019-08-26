import '@babel/polyfill'
import 'mutationobserver-shim'
import Vue from 'vue'
import './plugins/bootstrap-vue'
import App from './App.vue'


Vue.config.productionTip = false
Vue.filter('truncate', function (text, stop, clamp) {
    return text.slice(0, stop) + (stop < text.length ? clamp || '...' : '')
})

new Vue({
  render: h => h(App),
}).$mount('#app')
