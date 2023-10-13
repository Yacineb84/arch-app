export default {

    // partie template
    template: `<p :class='clazz'>{{msg}}</p>`,

    // partie propriétés
    props: {
        clazz: String, // une chaine
        text: {
            type: String, // une chaine
            required: true // obligatoire
        }
    },

    // les données de ce composant
    data() {
        return {
            msg: "Pas de message",
        }
    },

    // initialisation du composant
    mounted() {
        this.msg = this.text;
    },

    // les méthodes de ce composant
    methods: {
        change: function(e) {
            console.log("change message ");
            this.msg = e;
        },
        clear: function() {
            console.log("clear message ");
            this.msg = "";
        }
    }
}