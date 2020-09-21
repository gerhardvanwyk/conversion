<template>
    <div>
        <label >
            <select v-model="selected">
                <option disabled value="">Please select one</option>
                <option value="celsius-kelvin">Celsius - Kelvin</option>
                <option value="kelvin-celsius">Kelvin - Celsius</option>
                <option value="celsius-fahrenheit">Celsius - Fahrenheit</option>
                <option value="fahrenheit-celsius">Fahrenheit - Celsius</option>
                <option value="kilogram-pound">Kilogram - Pound</option>
                <option value="pound-kilogram">Pound - Kilogram</option>
                <option value="mile-kilometer">Mile - Kilometer</option>
                <option value="kilometer-mile">Kilometer - Mile</option>
                <option value="inch-centimeter">Inches - Centimeters</option>
                <option value="centimeter-inch">Centimeters - Inches</option>
            </select>
        </label>
        <span> : </span>
        <input v-model="value" placeholder="value to convert">
        <button v-on:click='answer = convert()'>Convert</button>
        <p>Conversion is: {{ answer }}</p>
    </div>
</template>

<script lang="ts">
    import Vue from 'vue'
    import axios, {AxiosResponse} from 'axios'

    export default Vue.extend({

        data() {
            return {
                selected: '',
                value: '',
                answer: ''

            }
        },
        methods: {
          convert(): void {
              let a: Promise<AxiosResponse<string>> ;
              console.log('localhost:8080/convert/'+ this.selected + '/' + this.value );
              a = axios.get('http://localhost:8080/convert/'+ this.selected + '/' + this.value )

                  .then(function (response) {
                      console.log(response.data);
                      return response.data;
                  })
                  .catch(response => console.error("Conversion failed: ", response)
                  );
              a.then(s => this.setAnswer(s))
                  .catch(r => console.error(r))
          },
          setAnswer(asw: AxiosResponse<string>):void{
              this.answer = asw.data;
          }
        },
    })
</script>

<style scoped>

</style>
