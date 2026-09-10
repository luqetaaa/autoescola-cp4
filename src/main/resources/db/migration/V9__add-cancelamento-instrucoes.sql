alter table instrucoes add column status varchar(20) not null default 'AGENDADA';
alter table instrucoes add column motivo_cancelamento varchar(30);
alter table instrucoes add column data_cancelamento datetime;
