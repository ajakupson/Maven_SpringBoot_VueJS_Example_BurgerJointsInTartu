import MainLayout from '../layouts/Main'
import VLink from '../components/VLink'

export default {
	template: `<main-layout>
				<h1>This Is Application Entry Point</h1>
				<p><v-link href="/burger-joints">Burger Joints</v-link></p>
			   </main-layout>`,
	components: {
	'v-link': VLink,
	'main-layout': MainLayout
    }
}