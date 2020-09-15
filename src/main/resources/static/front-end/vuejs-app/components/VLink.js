import routes from '../routes'

export default {
    template: '<a v-bind:href="href" v-bind:class="customClass" v-on:click="go"><slot></slot></a>',
    props: {
        href: {
            type:String,
            required: true 
        },
        customClass: {
            type: String,
            required: false
        }
    },
    computed: {
        isActive () {
            return this.href === this.$root.currentRoute
        }
    },
    methods: {
        go(event) {
            event.preventDefault();
            console.log("v-link clicked, href = ", this.href);
            this.$root.currentRoute = this.href;
            window.history.pushState(
                null,
                routes[this.href],
                this.href
            );
        }
    }
}