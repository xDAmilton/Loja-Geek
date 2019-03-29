var app = new Vue({
  el: '#app',
  data: {
    errors: [],
    email: null,
    senha: null,
    repsenha: null
  },
  methods: {
    checkForm () {

        this.errors = [];

      if (!this.email) {
        this.errors.push('O email é obrigatório.');
      }
      if (!this.senha) {
        this.errors.push('A senha é obrigatória.');
      }

      if(!this.repsenha) {
        this.errors.push('Repetir senha é obrigatório.');
      }

      if(this.senha != this.repsenha) {
        this.errors.push('As senhas estão diferentes.');
      }

      if (this.email && this.senha && this.repsenha) {
        return true;
      }
    }
  }
});
