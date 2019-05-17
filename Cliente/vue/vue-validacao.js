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

var app = new Vue({
  el: '#sac',
  data: {
    errors: [],
    email: null,
    duvida: null,
  },
  methods: {
    checkForm () {

        this.errors = [];

      if (!this.email) {
        this.errors.push('O email é obrigatório.');
      }
      if (!this.duvida) {
        this.errors.push('Nenhuma dúvida digitada.');
      }
      if (this.email && this.duvida) {
        return true;
      }
    }
  }
});

var app = new Vue({
  el: '#car',
  data: {
    errors: [],
    nome: null,
    email: null,
    cidade: null,
    estado: null,
    cep: null,
    ncart: null,
    numcart: null,
    venci: null,
    codseg: null,
  },
  methods: {
    checkForm () {

        this.errors = [];

      if (!this.nome) {
        this.errors.push('O Nome é obrigatório.');
      }
      if (!this.email) {
        this.errors.push('O Email é obrigatório.');
      }
      if (!this.cidade) {
        this.errors.push('A Cidade é obrigatório.');
      }
      if (!this.estado) {
        this.errors.push('O Estado é obrigatório.');
      }
      if (!this.cep) {
        this.errors.push('O CEP é obrigatório.');
      }
      if (!this.ncart) {
        this.errors.push('O nome do titular é obrigatório.');
      }
      if (!this.numcart) {
        this.errors.push('O Nº do Cartão é obrigatório.');
      }
      if (!this.venci) {
        this.errors.push('A data de vencimento é obrigatório.');
      }
      if (!this.codseg) {
        this.errors.push('O código de segurança é obrigatório.');
      }
      if (this.nome && this.email && this.cidade && this.estado && this.cep && this.ncart && this.numcart && this.venci && this.codseg) {
        return true;
      }
    }
  }
});
