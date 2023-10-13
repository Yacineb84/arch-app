
import Message from './message.js';

export default {

    // les composants utilisés
    // --------------------------------------------
    components: {
        "message": Message,
    },

    // le template
    // --------------------------------------------
    template: `<message ref="mes"
        clazz='alert alert-secondary' :text='counter.toString()'></message>`,

    // les données
    // --------------------------------------------
    data() {
        return {
            counter: 1000,
        }
    },

    // les méthodes
    // --------------------------------------------
    methods: {
        increment: function() {
            this.counter++;
            this.$refs.mes.change(this.counter.toString());
        }
    }
}