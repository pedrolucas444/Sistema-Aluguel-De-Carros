const apiBase = 'http://localhost:8080'; 
const qs = sel => document.querySelector(sel);

let currentCliente = null;

async function fetchVeiculos() {
  const res = await fetch(`${apiBase}/api/veiculos`);
  return res.ok ? res.json() : [];
}

async function fetchPedidos() {
  const res = await fetch(`${apiBase}/api/pedidos`);
  return res.ok ? res.json() : [];
}

async function registerHandler(e) {
  e.preventDefault();
  const body = {
    nome: qs('#r_nome').value,
    rg: qs('#r_rg').value,
    cpf: qs('#r_cpf').value,
    endereco: qs('#r_endereco').value,
    profissao: qs('#r_profissao').value,
    empregadores: qs('#r_empregadores').value.split(',').map(s=>s.trim()).filter(Boolean)
  };
  const res = await fetch(`${apiBase}/api/clientes`, {method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(body)});
  if (res.ok) {
    const data = await res.json();
    qs('#authMsg').textContent = `Registrado com id ${data.id}. Faça login usando o CPF.`;
    qs('#registerForm').reset();
  } else {
    qs('#authMsg').textContent = `Erro ao registrar: ${res.status}`;
  }
}

async function loginHandler(e) {
  e.preventDefault();
  const cpf = qs('#l_cpf').value.trim();
  const clientes = await fetch(`${apiBase}/api/clientes`).then(r=>r.json());
  const found = clientes.find(c=>c.cpf === cpf);
  if (!found) {
    qs('#authMsg').textContent = 'Usuário não encontrado. Cadastre-se primeiro.';
    return;
  }
  currentCliente = found;
  qs('#welcome').textContent = `Olá, ${found.nome}`;
  qs('#logoutBtn').style.display = 'inline-block';
  qs('#auth').style.display = 'none';
  qs('#pedidoSection').style.display = 'block';
  qs('#authMsg').textContent = '';
  await refreshVeiculosAndPedidos();
}

async function logout() {
  currentCliente = null;
  qs('#welcome').textContent = '';
  qs('#logoutBtn').style.display = 'none';
  qs('#auth').style.display = 'block';
  qs('#pedidoSection').style.display = 'none';
}

async function refreshVeiculosAndPedidos() {
  const veiculos = await fetchVeiculos();
  const sel = qs('#veiculoSelect');
  sel.innerHTML = '';
  veiculos.forEach(v=>{
    const opt = document.createElement('option');
    opt.value = v.id;
    opt.textContent = `${v.marca} ${v.modelo} (${v.placa})`;
    sel.appendChild(opt);
  });

  const pedidos = await fetchPedidos();
  const my = pedidos.filter(p=>p.cliente && p.cliente.id === currentCliente.id);
  const tbody = qs('#pedidosTable tbody');
  tbody.innerHTML = '';
  my.forEach(p=>{
    const tr = document.createElement('tr');
    tr.innerHTML = `<td>${p.id}</td><td>${p.veiculo ? p.veiculo.marca + ' ' + p.veiculo.modelo : ''}</td><td>${p.dataSolicitacao || ''}</td><td>${p.status}</td><td>${p.parecerFinanceiro || ''}</td>`;
    tbody.appendChild(tr);
  });
}

async function pedidoHandler(e) {
  e.preventDefault();
  if (!currentCliente) return;
  const vid = qs('#veiculoSelect').value;
  const res = await fetch(`${apiBase}/api/pedidos?clienteId=${currentCliente.id}&veiculoId=${vid}`, {method:'POST'});
  if (res.ok) {
    qs('#pedidoMsg').textContent = 'Pedido criado com sucesso.';
    await refreshVeiculosAndPedidos();
  } else {
    qs('#pedidoMsg').textContent = `Erro ao criar pedido: ${res.status}`;
  }
}

document.addEventListener('DOMContentLoaded', async ()=>{
  // login form
  qs('#loginForm').addEventListener('submit', loginHandler);
  qs('#logoutBtn').addEventListener('click', logout);
  // create pedido button
  qs('#createPedidoBtn').addEventListener('click', async (e)=>{
    if (!currentCliente) { qs('#pedidoMsg').textContent = 'Faça login antes.'; return; }
    const vid = qs('#veiculoSelect').value;
    const res = await fetch(`${apiBase}/api/pedidos?clienteId=${currentCliente.id}&veiculoId=${vid}`, {method:'POST'});
    if (res.ok) {
      qs('#pedidoMsg').textContent = 'Pedido criado com sucesso.';
      await refreshVeiculosAndPedidos();
    } else {
      let text = '';
      try { text = await res.text(); } catch(e){ text = '' }
      qs('#pedidoMsg').textContent = `Erro ao criar pedido: ${res.status} ${text}`;
    }
  });
  // pre-load vehicles
  const v = await fetchVeiculos();
  if (v && v.length) {
    // do nothing; select will be filled on login
  }
});
