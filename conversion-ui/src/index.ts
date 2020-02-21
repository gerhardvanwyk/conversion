import Vue from 'vue';
import vuetify from './plugins/vuetify'
// @ts-ignore
import Conversion from './components/conversion.vue'

Vue.use(vuetify);
const v = new Vue({
   el: '#app',
   vuetify,
   template: `
   <div>
    <conversion></conversion>
   </div>
   `,
   components: {
      Conversion,
   },
});
