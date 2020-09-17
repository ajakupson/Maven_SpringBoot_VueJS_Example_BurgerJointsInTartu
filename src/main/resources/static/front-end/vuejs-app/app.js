import routes from './routes'
import Home from './pages/Home'
import BurgerJoints from './pages/BurgerJoints'
import NotFound from './pages/NotFound'

var app = new Vue({
    el: '#app',
    components: {
		'Home': Home,
		'BurgerJoints': BurgerJoints,
        'NotFound': NotFound
    },
    created: function () {
      console.log("app launched");
      console.log("this.currentRoute", this.currentRoute);
    },
    data: {
		currentRoute: window.location.pathname,
		fourSquareClientId: "ULTN0IESIICWPHWBN1RHEVGCK3HXNN1TD2MQ1PT2M0AZVNQL",
		fourSquareClientSecret: "RCGHJXDJ1J5KDCO3BT3NB5P0BAT3I0DXMM4YJXWMVX3AWNV2"
 	},
    computed: {
        ViewComponent() {
            var apiRoute = this.currentRoute.slice(0, this.currentRoute.lastIndexOf("/") + 1) + "*";
            var isApiRoute = apiRoute in routes;
            if (isApiRoute) return routes[apiRoute];
            else return routes[this.currentRoute] || NotFound;
        }
    },
    render(h) { return h(this.ViewComponent) }
});

window.addEventListener('popstate', () => {
    app.currentRoute = window.location.pathname;
});

let port = process.env.PORT;
if (port == null || port == "") {
  port = 8000;
}
app.listen(port);
